import { Test, TestingModule } from '@nestjs/testing';
import { CUSTOMERS_SERVICE, ICustomersService } from './customers.service';
import { CustomersResolver } from './customers.resolver';
import { CustomerInput } from './dtos/customer.input';

describe('CustomersResolver', () => {
  let resolver: CustomersResolver;
  let service: ICustomersService;

  const mockCustomersService = {
    findAllCustomers: jest.fn(),
    findCustomerById: jest.fn(),
    findCustomerByIdentificationNumber: jest.fn(),
    createCustomer: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CustomersResolver,
        {
          provide: CUSTOMERS_SERVICE,
          useValue: mockCustomersService,
        },
      ],
    }).compile();

    resolver = module.get<CustomersResolver>(CustomersResolver);
    service = module.get<ICustomersService>(CUSTOMERS_SERVICE);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('getAllCustomers', () => {
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

      jest.spyOn(service, 'findAllCustomers').mockResolvedValue(customers);

      const result = await resolver.getAllCustomers();

      expect(result).toEqual(customers);
      expect(service.findAllCustomers).toHaveBeenCalled();
    });
  });

  describe('getCustomerById', () => {
    it('should return a customer when valid ID is provided', async () => {
      const customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };

      jest.spyOn(service, 'findCustomerById').mockResolvedValue(customer);

      const result = await resolver.getCustomerById('1');

      expect(result).toEqual(customer);
      expect(service.findCustomerById).toHaveBeenCalledWith('1');
    });
  });

  describe('getCustomerByIdentificationNumber', () => {
    it('should return a customer when valid identification number is provided', async () => {
      const customer = {
        id: '1',
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };

      jest
        .spyOn(service, 'findCustomerByIdentificationNumber')
        .mockResolvedValue(customer);

      const result =
        await resolver.getCustomerByIdentificationNumber('1234567890');

      expect(result).toEqual(customer);
      expect(service.findCustomerByIdentificationNumber).toHaveBeenCalledWith(
        '1234567890',
      );
    });
  });

  describe('createCustomer', () => {
    it('should create and return a new customer', async () => {
      const input: CustomerInput = {
        identificationNumber: '1234567890',
        name: 'John Doe',
        email: 'john.doe@example.com',
      };
      const createdCustomer = {
        id: '1',
        ...input,
      };

      jest.spyOn(service, 'createCustomer').mockResolvedValue(createdCustomer);

      const result = await resolver.createCustomer(input);

      expect(result).toEqual(createdCustomer);
      expect(service.createCustomer).toHaveBeenCalledWith(input);
    });
  });
});
