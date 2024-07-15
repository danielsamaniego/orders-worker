import { Test, TestingModule } from '@nestjs/testing';
import { CustomersController } from './customers.controller';
import { CUSTOMERS_SERVICE, ICustomersService } from './customers.service';
import { CreateCustomerRequestDto } from './dtos/create-customer.request.dto';
import { CustomerResponseDto } from './dtos/customer.response.dto';

describe('CustomersController', () => {
  let controller: CustomersController;
  let service: ICustomersService;

  const mockCustomersService = {
    createCustomer: jest.fn(),
    findAllCustomers: jest.fn(),
    findCustomerById: jest.fn(),
    findCustomerByIdentificationNumber: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CustomersController],
      providers: [
        {
          provide: CUSTOMERS_SERVICE,
          useValue: mockCustomersService,
        },
      ],
    }).compile();

    controller = module.get<CustomersController>(CustomersController);
    service = module.get<ICustomersService>(CUSTOMERS_SERVICE);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('create', () => {
    it('should create and return a new customer', async () => {
      const createCustomerDto: CreateCustomerRequestDto = {
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      const createdCustomer: CustomerResponseDto = {
        id: '1',
        ...createCustomerDto,
      };

      jest.spyOn(service, 'createCustomer').mockResolvedValue(createdCustomer);

      const result = await controller.create(createCustomerDto);

      expect(result).toEqual(createdCustomer);
      expect(service.createCustomer).toHaveBeenCalledWith(createCustomerDto);
    });
  });

  describe('findAll', () => {
    it('should return an array of customers', async () => {
      const customers: CustomerResponseDto[] = [
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

      jest.spyOn(service, 'findAllCustomers').mockResolvedValue(customers);

      const result = await controller.findAll();

      expect(result).toEqual(customers);
      expect(service.findAllCustomers).toHaveBeenCalled();
    });
  });

  describe('findOne', () => {
    it('should return a customer when valid ID is provided', async () => {
      const customer: CustomerResponseDto = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };

      jest.spyOn(service, 'findCustomerById').mockResolvedValue(customer);

      const result = await controller.findOne('1');

      expect(result).toEqual(customer);
      expect(service.findCustomerById).toHaveBeenCalledWith('1');
    });
  });

  describe('findOneByIdentificationNumber', () => {
    it('should return a customer when valid identification number is provided', async () => {
      const customer: CustomerResponseDto = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };

      jest
        .spyOn(service, 'findCustomerByIdentificationNumber')
        .mockResolvedValue(customer);

      const result =
        await controller.findOneByIdentificationNumber('1234567890');

      expect(result).toEqual(customer);
      expect(service.findCustomerByIdentificationNumber).toHaveBeenCalledWith(
        '1234567890',
      );
    });
  });
});
