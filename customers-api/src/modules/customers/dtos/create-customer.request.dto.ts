import { IsNotEmpty, IsString, IsOptional, IsEmail } from 'class-validator';

export class CreateCustomerRequestDto {
  @IsNotEmpty()
  @IsString()
  identificationNumber: string;

  @IsNotEmpty()
  @IsString()
  name: string;

  @IsOptional()
  @IsString()
  @IsEmail()
  email?: string | null;
}
