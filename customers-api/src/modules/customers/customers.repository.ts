import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { ConflictException, Injectable, NotFoundException } from "@nestjs/common";
import { Customer } from './models/customer.model';

export const CUSTOMERS_REPOSITORY = 'CUSTOMERS_REPOSITORY';

// ICustomerRepository is an interface that defines the methods for managing in database.
export interface ICustomersRepository {
  // createCustomer method creates a new customer.
  createCustomer({
    identificationNumber,
    name,
    email,
  }: Customer): Promise<Customer>;
  // findAllCustomers method returns all customers.
  findAllCustomers(): Promise<Customer[]>;
  // findCustomerById method returns a customer by id.
  findCustomerById(id: string): Promise<Customer>;
  // findByIdentificationNumber method returns a customer by identification number.
  findByIdentificationNumber(identificationNumber: string): Promise<Customer>;
}

// CustomerRepository is a class that implements the ICustomerRepository interface.
@Injectable()
export class CustomersRepository implements ICustomersRepository {
  constructor(
    @InjectModel(Customer.name) private customerModel: Model<Customer>,
  ) {}

  // createCustomer is an implementation of the createCustomer method from the ICustomerRepository interface.
  async createCustomer({
    identificationNumber,
    name,
    email,
  }: Customer): Promise<Customer> {
    const newCustomer = new this.customerModel({
      identificationNumber,
      name,
      email,
    });
    try {
      return await newCustomer.save();
    } catch (error) {
      if (error.code === 11000) {
        throw new ConflictException(
          'Customer already exists with this identification number',
        );
      }
      throw error;
    }
  }

  // findAllCustomers is an implementation of the findAllCustomers method from the ICustomerRepository interface.
  async findAllCustomers(): Promise<Customer[]> {
    return this.customerModel.find().exec();
  }

  // findCustomerById is an implementation of the findCustomerById method from the ICustomerRepository interface.
  async findCustomerById(id: string): Promise<Customer> {
    const customer = await this.customerModel.findOne({id}).exec();
    if (!customer) {
      throw new NotFoundException('Customer not found');
    }
    return customer;
  }

  // findByIdentificationNumber is an implementation of the findByIdentificationNumber method from the ICustomerRepository interface.
  async findByIdentificationNumber(
    identificationNumber: string,
  ): Promise<Customer> {
    const customer = await this.customerModel.findOne({ identificationNumber }).exec();
    if (!customer) {
      throw new NotFoundException('Customer not found');
    }
    return customer;
  }
}
