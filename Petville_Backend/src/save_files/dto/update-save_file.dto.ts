import { PartialType } from '@nestjs/mapped-types';
import { CreateSaveFileDto } from './create-save_file.dto';

export class UpdateSaveFileDto extends PartialType(CreateSaveFileDto) {
    user_id?: number;
    petName?: string;
    petType?: string;
    petEnergy?: number ;
    petHunger?: number;
    petMood?: number;
    petHealth?: number;
    hoursPlayer?: number;
    goldEarned?: number;
    currentGold?: number;
}
