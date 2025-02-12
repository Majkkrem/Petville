import { PartialType } from '@nestjs/mapped-types';
import { CreateSaveFileDto } from './create-save_file.dto';

export class UpdateSaveFileDto extends PartialType(CreateSaveFileDto) {
    file_name?: string;
    file_path?: string;
}
