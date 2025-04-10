import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { hash } from 'argon2';

@Injectable()
export class UsersService {
  constructor(private prisma: PrismaService) {}

 async create(createUserDto: CreateUserDto) {
    const hashedPassword = await hash(createUserDto.password);
    const newUser = await this.prisma.users.create({
      data: {
        ...createUserDto,
        password: hashedPassword,
      },
    });
    console.log(createUserDto);
    delete newUser.password;
    return newUser;
  }

 async findAll() {
    const users =  await this.prisma.users.findMany();
    users.map(user => delete user.password);
    return users;
  }

  async findOne(id: number) {
    const user =  await this.prisma.users.findUnique({ where: { id } });
    delete user.password;
    return user;
  }

  update(id: number, updateUserDto: UpdateUserDto) {
    return this.prisma.users.update({ where: { id }, data: updateUserDto });
  }

  remove(id: number) {
    return this.prisma.users.delete({ where: { id } });
  }
}