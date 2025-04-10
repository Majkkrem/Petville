import { Injectable, NotFoundException } from '@nestjs/common';
import { CreateInventoryDto } from './dto/create-inventory.dto';
import { UpdateInventoryDto } from './dto/update-inventory.dto';

@Injectable()
export class InventoriesService {
  private inventories = []; 

  create(createInventoryDto: CreateInventoryDto) {
    const newInventory = {
      id: this.inventories.length + 1, 
      ...createInventoryDto,
    };
    this.inventories.push(newInventory);
    return newInventory;
  }

  findAll() {
    return this.inventories;
  }

  findOne(id: number) {
    const inventory = this.inventories.find((item) => item.id === id);
    if (!inventory) {
      throw new NotFoundException(`Inventory with ID ${id} not found`);
    }
    return inventory;
  }

  update(id: number, updateInventoryDto: UpdateInventoryDto) {
    const inventoryIndex = this.inventories.findIndex((item) => item.id === id);
    if (inventoryIndex === -1) {
      throw new NotFoundException(`Inventory with ID ${id} not found`);
    }
    const updatedInventory = {
      ...this.inventories[inventoryIndex],
      ...updateInventoryDto,
    };
    this.inventories[inventoryIndex] = updatedInventory;
    return updatedInventory;
  }

  remove(id: number) {
    const inventoryIndex = this.inventories.findIndex((item) => item.id === id);
    if (inventoryIndex === -1) {
      throw new NotFoundException(`Inventory with ID ${id} not found`);
    }
    const removedInventory = this.inventories.splice(inventoryIndex, 1);
    return removedInventory[0];
  }
}