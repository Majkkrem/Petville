import { PrismaClient } from '@prisma/client';
import { faker } from '@faker-js/faker';

const prisma = new PrismaClient();

async function main() {
  // Create 10 regular users
  for (let i = 0; i < 10; i++) {
    const user = await prisma.users.create({
      data: {
        name: faker.internet.userName(),
        email: faker.internet.email(),
        password: faker.internet.password(),
        role: 'USER',
      },
    });

    // Create leaderboard entry for each user
    await prisma.leaderboard.create({
      data: {
        user_id: user.id,
        score: faker.number.int({ min: 0, max: 10000 }),
      },
    });

    // Create inventory for each user
    const inventory = await prisma.inventory.create({
      data: {
        user_id: user.id,
        currentFood: faker.number.int({ min: 0, max: 20 }),
        currentHeal: faker.number.int({ min: 0, max: 10 }),
        currentEnergyBar: faker.number.int({ min: 0, max: 5 }),
      },
    });

    // Create save file for each user (50% chance)
    if (faker.datatype.boolean()) {
      await prisma.save_files.create({
        data: {
          user_id: user.id,
          petName: faker.person.firstName(),
          petType: faker.helpers.arrayElement(['Cat', 'Dog', 'Bird', 'Dragon', 'Unicorn']),
          petEnergy: faker.number.int({ min: 0, max: 100 }),
          petHunger: faker.number.int({ min: 0, max: 100 }),
          petMood: faker.number.int({ min: 0, max: 100 }),
          petHealth: faker.number.int({ min: 0, max: 100 }),
          hoursPlayer: faker.number.int({ min: 0, max: 500 }),
          goldEarned: faker.number.int({ min: 0, max: 10000 }),
          currentGold: faker.number.int({ min: 0, max: 5000 }),
          // Ensure the inventory field is compatible with the Prisma schema or remove it if not supported
          // inventory: { connect: { id: inventory.id } },
        },
      });
    }
  }

  // Create 2 admin users
  for (let i = 0; i < 2; i++) {
    const admin = await prisma.users.create({
      data: {
        name: `admin_${faker.internet.userName()}`,
        email: `admin_${faker.internet.email()}`,
        password: faker.internet.password(),
        role: 'ADMIN',
      },
    });

    // Create leaderboard entry for admin (optional)
    if (faker.datatype.boolean()) {
      await prisma.leaderboard.create({
        data: {
          user_id: admin.id,
          score: faker.number.int({ min: 0, max: 10000 }),
        },
      });
    }
  }

  console.log('Seeding completed successfully!');
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });