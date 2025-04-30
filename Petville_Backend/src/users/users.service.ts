import { BadRequestException, Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { hash } from 'argon2';

@Injectable()
export class UsersService {
  constructor(private prisma: PrismaService) {}

 async create(createUserDto: CreateUserDto) {
    const hashedPassword = await hash(createUserDto.password);
    let newUser;
    try{
      newUser = await this.prisma.users.create({
        data: {
          ...createUserDto,
          password: hashedPassword,
        },
      });
    }
    catch (error) {
      if (error.code === 'P2002') {
        throw new BadRequestException(error.meta.target);
      } else {
        throw error;
      }
    }

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