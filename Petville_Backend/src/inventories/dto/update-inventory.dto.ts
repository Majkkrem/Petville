import { PartialType } from '@nestjs/swagger';
import { CreateInventoryDto } from './create-inventory.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateInventoryDto extends PartialType(CreateInventoryDto) {
  @ApiProperty({ description: 'ID of the user who owns the inventory', example: 1, required: false })
  userId?: number;

  @ApiProperty({ description: 'Name of the inventory item', example: 'Health Potion', required: false })
  itemName?: string;

  @ApiProperty({ description: 'Quantity of the inventory item', example: 10, required: false })
  quantity?: number;

  @ApiProperty({ description: 'Description of the inventory item', example: 'Restores 50 HP', required: false })
  description?: string;
}
