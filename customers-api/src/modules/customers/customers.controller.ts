import { Controller, Get, Post, Body, Param, Inject, Query } from '@nestjs/common';
import { CUSTOMERS_SERVICE, ICustomersService } from './customers.service';
import { CreateCustomerRequestDto } from './dtos/create-customer.request.dto';
import { CustomerResponseDto } from './dtos/customer.response.dto';

@Controller('customer')
export class CustomersController {
  constructor(
    @Inject(CUSTOMERS_SERVICE)
    private readonly customersService: ICustomersService,
  ) {}

  @Post()
  async create(
    @Body() createCustomerDto: CreateCustomerRequestDto,
  ): Promise<CustomerResponseDto> {
    return this.customersService.createCustomer(createCustomerDto);
  }

  @Get()
  async findAll(): Promise<CustomerResponseDto[]> {
    return this.customersService.findAllCustomers();
  }

  @Get('id/:id')
  async findOne(@Param('id') id: string): Promise<CustomerResponseDto> {
    return this.customersService.findCustomerById(id);
  }

  // Get by identification number with query params
  @Get('identification-number/:identificationNumber')
  async findOneByIdentificationNumber(
    @Param('identificationNumber') identificationNumber: string,
  ): Promise<CustomerResponseDto> {
    return this.customersService.findCustomerByIdentificationNumber(
      identificationNumber,
    );
  }
}
