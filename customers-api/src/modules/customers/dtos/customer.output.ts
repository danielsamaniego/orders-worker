import { Field, ID, ObjectType } from '@nestjs/graphql';

@ObjectType()
export class CustomerOutput {
  @Field(() => ID)
  id: string;

  @Field()
  identificationNumber: string;

  @Field()
  name: string;

  @Field()
  email?: string | null;
}
