import { PartialType } from '@nestjs/swagger';
import { CreateLoadDto } from './create-load.dto';

export class UpdateLoadDto extends PartialType(CreateLoadDto) {}
