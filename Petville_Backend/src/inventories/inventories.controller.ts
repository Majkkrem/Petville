import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiParam, ApiBody, ApiResponse } from '@nestjs/swagger';
import { InventoriesService } from './inventories.service';
import { CreateInventoryDto } from './dto/create-inventory.dto';
import { UpdateInventoryDto } from './dto/update-inventory.dto';

@ApiTags('Inventories') 
@Controller('inventories')
export class InventoriesController {
  constructor(private readonly inventoriesService: InventoriesService) {}

  @Post()
  @ApiOperation({ summary: 'Create a new inventory item' })
  @ApiBody({ type: CreateInventoryDto })
  @ApiResponse({ status: 201, description: 'Inventory item created successfully.' })
  create(@Body() createInventoryDto: CreateInventoryDto) {
    return this.inventoriesService.create(createInventoryDto);
  }

  @Get()
  @ApiOperation({ summary: 'Retrieve all inventory items' })
  @ApiResponse({ status: 200, description: 'List of inventory items retrieved successfully.' })
  findAll() {
    return this.inventoriesService.findAll();
  }

  @Get(':id')
  @ApiOperation({ summary: 'Retrieve a specific inventory item by ID' })
  @ApiParam({ name: 'id', description: 'ID of the inventory item' })
  @ApiResponse({ status: 200, description: 'Inventory item retrieved successfully.' })
  @ApiResponse({ status: 404, description: 'Inventory item not found.' })
  findOne(@Param('id') id: string) {
    return this.inventoriesService.findOne(+id);
  }

  @Patch(':id')
  @ApiOperation({ summary: 'Update a specific inventory item by ID' })
  @ApiParam({ name: 'id', description: 'ID of the inventory item' })
  @ApiBody({ type: UpdateInventoryDto })
  @ApiResponse({ status: 200, description: 'Inventory item updated successfully.' })
  @ApiResponse({ status: 404, description: 'Inventory item not found.' })
  update(@Param('id') id: string, @Body() updateInventoryDto: UpdateInventoryDto) {
    return this.inventoriesService.update(+id, updateInventoryDto);
  }

  @Delete(':id')
  @ApiOperation({ summary: 'Delete a specific inventory item by ID' })
  @ApiParam({ name: 'id', description: 'ID of the inventory item' })
  @ApiResponse({ status: 200, description: 'Inventory item deleted successfully.' })
  @ApiResponse({ status: 404, description: 'Inventory item not found.' })
  remove(@Param('id') id: string) {
    return this.inventoriesService.remove(+id);
  }
}