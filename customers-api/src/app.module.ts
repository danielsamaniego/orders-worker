import { Module } from '@nestjs/common';
import { CustomersModule } from './modules/customers/customers.module';
import { CommonModule } from './common/common.module';

@Module({
  imports: [CommonModule, CustomersModule],
})
export class AppModule {}
