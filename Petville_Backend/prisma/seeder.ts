// seed.ts
import { faker } from '@faker-js/faker';
import { PrismaClient, Role, Users } from '@prisma/client';
const prisma = new PrismaClient();

async function main() {
  // Clear existing data
  await prisma.inventory.deleteMany();
  await prisma.leaderboard.deleteMany();
  await prisma.save_files.deleteMany();
  await prisma.users.deleteMany();

  // Create users
  const users: Users[] = [];
  for (let i = 0; i < 20; i++) {
    const user = await prisma.users.create({
      data: {
        name: faker.internet.userName(),
        email: faker.internet.email(),
        password: faker.internet.password(),
        role: i === 0 ? Role.ADMIN : Role.USER,
        dateOfRegister: faker.date.past({ years: 1 }),
      },
    });
    users.push(user);
  }

  // Create save files with inventory
  const petTypes = ['dog', 'cat', 'bee', 'frog'];
  
  for (const user of users) {
    // Create 1-3 save files per user
    const saveCount = faker.number.int({ min: 1, max: 3 });
    const saves: Array<{
      id: number;
      user_id: number;
      petName: string;
      petType: string;
      petEnergy: number;
      petHunger: number;
      petMood: number;
      petHealth: number;
      hoursPlayer: number;
      goldEarned: number;
      currentGold: number;
    }> = [];
    
    for (let i = 0; i < saveCount; i++) {
      const goldEarned = faker.number.int({ min: 100, max: 5000 });
      const save = await prisma.save_files.create({
        data: {
          user: {
            connect: { id: user.id },
          },
          petName: faker.person.firstName(),
          petType: faker.helpers.arrayElement(petTypes),
          petEnergy: faker.number.int({ min: 20, max: 100 }),
          petHunger: faker.number.int({ min: 20, max: 100 }),
          petMood: faker.number.int({ min: 20, max: 100 }),
          petHealth: faker.number.int({ min: 20, max: 100 }),
          hoursPlayer: faker.number.int({ min: 1, max: 100 }),
          goldEarned: goldEarned,
          currentGold: faker.number.int({ 
            min: Math.floor(goldEarned * 0.1), 
            max: Math.floor(goldEarned * 0.9) 
          }),
          inventory: {
            create: {
              currentFood: faker.number.int({ min: 0, max: 20 }),
              currentHeal: faker.number.int({ min: 0, max: 10 }),
              currentEnergyBar: faker.number.int({ min: 0, max: 15 }),
              user: {
                connect: { id: user.id },
              },
            }
          }
        }
      });
      saves.push(save);
    }

    // Find the save with highest goldEarned
    const highestGoldSave = saves.reduce((prev, current) => 
      (prev.goldEarned > current.goldEarned) ? prev : current
    );

    // Create leaderboard entry linked to this save
    await prisma.leaderboard.create({
      data: {
        user_id: user.id,
        score: highestGoldSave.goldEarned,
        save_file_id: highestGoldSave.id
      }
    });
  }

  console.log('Database seeded successfully!');
}

main()
  .catch((e) => {
    console.error('Error seeding database:', e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });