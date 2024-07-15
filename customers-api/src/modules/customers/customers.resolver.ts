import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { CUSTOMERS_SERVICE, ICustomersService } from './customers.service';
import { Inject } from '@nestjs/common';
import { CustomerOutput } from './dtos/customer.output';
import { CustomerInput } from './dtos/customer.input';

@Resolver(() => CustomerOutput)
export class CustomersResolver {
  constructor(
    @Inject(CUSTOMERS_SERVICE)
    private readonly customersService: ICustomersService,
  ) {}

  @Query(() => [CustomerOutput], { name: 'customers' })
  async getAllCustomers(): Promise<CustomerOutput[]> {
    const customers = await this.customersService.findAllCustomers();

    return customers.map((customer) => ({
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    }));
  }

  @Query(() => CustomerOutput, { name: 'customer' })
  async getCustomerById(@Args('id') id: string): Promise<CustomerOutput> {
    const customer = await this.customersService.findCustomerById(id);
    return {
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    };
  }

  @Query(() => CustomerOutput, { name: 'customerByIdentificationNumber' })
  async getCustomerByIdentificationNumber(
    @Args('identificationNumber') identificationNumber: string,
  ): Promise<CustomerOutput> {
    const customer =
      await this.customersService.findCustomerByIdentificationNumber(
        identificationNumber,
      );
    return {
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    };
  }

  @Mutation(() => CustomerOutput, { name: 'createCustomer' })
  async createCustomer(
    @Args('input') input: CustomerInput,
  ): Promise<CustomerOutput> {
    const customer = await this.customersService.createCustomer(input);
    return {
      id: customer.id,
      identificationNumber: customer.identificationNumber,
      name: customer.name,
      email: customer.email,
    };
  }
}
