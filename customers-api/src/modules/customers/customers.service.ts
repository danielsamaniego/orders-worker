import { Inject, Injectable } from '@nestjs/common';
import { CreateCustomerRequestDto } from './dtos/create-customer.request.dto';
import {
  CUSTOMERS_REPOSITORY,
  ICustomersRepository,
} from './customers.repository';
import { Customer } from './models/customer.model';

export const CUSTOMERS_SERVICE = 'CUSTOMERS_SERVICE';

// ICustomersService is an interface that defines the methods for managing customers.
export interface ICustomersService {
  // create method creates a new customer.
  createCustomer(request: CreateCustomerRequestDto): Promise<Customer>;
  // findAllCustomers method returns all customers.
  findAllCustomers(): Promise<Customer[]>;
  // findCustomerById method returns a customer by id.
  findCustomerById(id: string): Promise<Customer>;
  // findCustomerByIdentificationNumber method returns a customer by identification number.
  findCustomerByIdentificationNumber(identificationNumber: string): Promise<Customer>;
}

@Injectable()
export class CustomersService implements ICustomersService {
  constructor(
    @Inject(CUSTOMERS_REPOSITORY)
    private readonly customersRepository: ICustomersRepository,
  ) {}

  // createCustomer is an implementation of the createCustomer method from the ICustomersService interface.
  async createCustomer(request: CreateCustomerRequestDto): Promise<Customer> {
    const createdCustomer = await this.customersRepository.createCustomer({
      identificationNumber: request.identificationNumber,
      name: request.name,
      email: request.email,
    } as Customer);

    return {
      id: createdCustomer.id,
      identificationNumber: createdCustomer.identificationNumber,
      name: createdCustomer.name,
      email: createdCustomer.email,
    };
  }

  // findAllCustomers is an implementation of the findAllCustomers method from the ICustomersService interface.
  async findAllCustomers(): Promise<Customer[]> {
    const customers = await this.customersRepository.findAllCustomers();
    return customers.map((customer) => ({
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    }));
  }

  // findCustomerById is an implementation of the findCustomerById method from the ICustomersService interface.
  async findCustomerById(id: string): Promise<Customer> {
    const customer = await this.customersRepository.findCustomerById(id);
    return {
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    };
  }

  // findCustomerByIdentificationNumber is an implementation of the findCustomerByIdentificationNumber method from the ICustomersService interface.
  async findCustomerByIdentificationNumber(identificationNumber: string): Promise<Customer> {
    const customer = await this.customersRepository.findByIdentificationNumber(identificationNumber);
    return {
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    };
  }
}
