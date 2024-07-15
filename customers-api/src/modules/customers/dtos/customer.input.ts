import { InputType, Field } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsOptional, IsEmail } from 'class-validator';

@InputType()
export class CustomerInput {
  @Field()
  @IsNotEmpty()
  @IsString()
  identificationNumber: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  name: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  @IsEmail()
  email?: string | null;
}
