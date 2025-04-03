import { ApiProperty } from '@nestjs/swagger';

export class CreateInventoryDto {
  @ApiProperty({ description: 'ID of the user who owns the inventory', example: 1 })
  userId: number;

  @ApiProperty({ description: 'Name of the inventory item', example: 'Health Potion' })
  itemName: string;

  @ApiProperty({ description: 'Quantity of the inventory item', example: 10 })
  quantity: number;

  @ApiProperty({ description: 'Description of the inventory item', example: 'Restores 50 HP' })
  description: string;
}