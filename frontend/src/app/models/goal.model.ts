import { Rewards } from './rewards.enum'; // Assuming you have this enum defined separately

export interface Goal {
  goalId: number;           // Auto-generated primary key
  employeeId: number;       // ID of the employee who owns the goal
  goalType: string;         // Type/category of the goal
  targetDate: string;       // ISO date string (e.g., '2025-09-18')
  status: number;           // Status code (e.g., 0 = pending, 1 = completed)
  targetScores: number;     // Performance score target
  targetRewards: Rewards;   // Enum value like Rewards.BRONZE, Rewards.SILVER, etc.
  description: string;      // Description of the goal
}
