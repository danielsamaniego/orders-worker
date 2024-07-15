import { Test, TestingModule } from '@nestjs/testing';
import { getModelToken } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { NotFoundException } from '@nestjs/common';
import {
  CUSTOMERS_REPOSITORY,
  CustomersRepository,
} from './customers.repository';
import { Customer } from './models/customer.model';

describe('CustomersRepository', () => {
  let repository: CustomersRepository;
  let model: Model<Customer>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        {
          provide: CUSTOMERS_REPOSITORY,
          useClass: CustomersRepository,
        },
        {
          provide: getModelToken(Customer.name),
          useValue: {
            create: jest.fn().mockResolvedValue({}),
            save: jest.fn(),
            find: jest.fn(),
            findOne: jest.fn(),
            exec: jest.fn(),
          },
        },
      ],
    }).compile();

    repository = module.get<CustomersRepository>(CUSTOMERS_REPOSITORY);
    model = module.get<Model<Customer>>(getModelToken(Customer.name));
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('findAllCustomers', () => {
    it('should return an array of customers', async () => {
      const customers = [
        {
          id: '1',
          identificationNumber: '1234567890',
          name: 'John Doe',
          email: 'john.doe@example.com',
        },
        {
          id: '2',
          identificationNumber: '0987654321',
          name: 'Jane Doe',
          email: 'jane.doe@example.com',
        },
      ];
      jest.spyOn(model, 'find').mockReturnValue({
        exec: jest.fn().mockResolvedValue(customers),
      } as any);

      const result = await repository.findAllCustomers();

      expect(result).toEqual(customers);
      expect(model.find).toHaveBeenCalled();
    });
  });

  describe('findCustomerById', () => {
    it('should return a customer when valid ID is provided', async () => {
      const customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      jest.spyOn(model, 'findOne').mockReturnValue({
        exec: jest.fn().mockResolvedValue(customer),
      } as any);

      const result = await repository.findCustomerById('1');

      expect(result).toEqual(customer);
      expect(model.findOne).toHaveBeenCalledWith({ id: '1' });
    });

    it('should throw NotFoundException when customer is not found', async () => {
      jest.spyOn(model, 'findOne').mockReturnValue({
        exec: jest.fn().mockResolvedValue(null),
      } as any);

      await expect(repository.findCustomerById('1')).rejects.toThrow(
        NotFoundException,
      );
    });
  });

  describe('findByIdentificationNumber', () => {
    it('should return a customer when valid identification number is provided', async () => {
      const customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      jest.spyOn(model, 'findOne').mockReturnValue({
        exec: jest.fn().mockResolvedValue(customer),
      } as any);

      const result = await repository.findByIdentificationNumber('1234567890');

      expect(result).toEqual(customer);
      expect(model.findOne).toHaveBeenCalledWith({
        identificationNumber: '1234567890',
      });
    });

    it('should throw NotFoundException when customer is not found', async () => {
      jest.spyOn(model, 'findOne').mockReturnValue({
        exec: jest.fn().mockResolvedValue(null),
      } as any);

      await expect(
        repository.findByIdentificationNumber('1234567890'),
      ).rejects.toThrow(NotFoundException);
    });
  });
});
