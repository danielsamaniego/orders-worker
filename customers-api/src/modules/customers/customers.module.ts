import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { CustomersController } from './customers.controller';
import { CustomersService, CUSTOMERS_SERVICE } from './customers.service';
import {
  CustomersRepository,
  CUSTOMERS_REPOSITORY,
} from './customers.repository';
import { CustomerSchema, Customer } from './models/customer.schema';
import { CustomersResolver } from './customers.resolver';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: Customer.name, schema: CustomerSchema },
    ]),
  ],
  controllers: [CustomersController],
  providers: [
    {
      provide: CUSTOMERS_REPOSITORY,
      useClass: CustomersRepository,
    },
    {
      provide: CUSTOMERS_SERVICE,
      useClass: CustomersService,
    },
    CustomersResolver,
  ],
  exports: [CUSTOMERS_REPOSITORY, CUSTOMERS_SERVICE],
})
export class CustomersModule {}
