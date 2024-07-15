import { Test, TestingModule } from '@nestjs/testing';
import { CUSTOMERS_SERVICE, CustomersService } from './customers.service';
import {
  CUSTOMERS_REPOSITORY,
  ICustomersRepository,
} from './customers.repository';
import { CreateCustomerRequestDto } from './dtos/create-customer.request.dto';
import { Customer } from './models/customer.model';

describe('CustomersService', () => {
  let service: CustomersService;
  let repository: ICustomersRepository;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        {
          provide: CUSTOMERS_SERVICE,
          useClass: CustomersService,
        },
        {
          provide: CUSTOMERS_REPOSITORY,
          useValue: {
            createCustomer: jest.fn(),
            findAllCustomers: jest.fn(),
            findCustomerById: jest.fn(),
            findByIdentificationNumber: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<CustomersService>(CUSTOMERS_SERVICE);
    repository = module.get<ICustomersRepository>(CUSTOMERS_REPOSITORY);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('createCustomer', () => {
    it('should create and return a customer when valid request is provided', async () => {
      const createCustomerDto: CreateCustomerRequestDto = {
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      const createdCustomer: Customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      jest
        .spyOn(repository, 'createCustomer')
        .mockResolvedValue(createdCustomer);

      const result = await service.createCustomer(createCustomerDto);

      expect(result).toEqual(createdCustomer);
      expect(repository.createCustomer).toHaveBeenCalledWith(
        expect.objectContaining(createCustomerDto),
      );
    });
  });

  describe('findAllCustomers', () => {
    it('should return an array of customers when called', async () => {
      const customers: Customer[] = [
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
      jest.spyOn(repository, 'findAllCustomers').mockResolvedValue(customers);

      const result = await service.findAllCustomers();

      expect(result).toEqual(customers);
      expect(repository.findAllCustomers).toHaveBeenCalled();
    });
  });

  describe('findCustomerById', () => {
    it('should return a customer when valid ID is provided', async () => {
      const customer: Customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      jest.spyOn(repository, 'findCustomerById').mockResolvedValue(customer);

      const result = await service.findCustomerById('1');

      expect(result).toEqual(customer);
      expect(repository.findCustomerById).toHaveBeenCalledWith('1');
    });
  });

  describe('findCustomerByIdentificationNumber', () => {
    it('should return a customer when valid identification number is provided', async () => {
      const customer: Customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      jest
        .spyOn(repository, 'findByIdentificationNumber')
        .mockResolvedValue(customer);

      const result =
        await service.findCustomerByIdentificationNumber('1234567890');

      expect(result).toEqual(customer);
      expect(repository.findByIdentificationNumber).toHaveBeenCalledWith(
        '1234567890',
      );
    });
  });
});
