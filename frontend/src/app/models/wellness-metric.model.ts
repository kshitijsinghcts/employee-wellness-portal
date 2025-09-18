import { Rewards } from './rewards.enum'; // Assuming you created a separate enum file

export interface WellnessMetric {
  metricId: number;             // Auto-generated primary key
  employeeId: number;           // ID of the employee
  recordDate: string;           // ISO date string (e.g., '2025-09-18')
  mood: string;                 // Mood description
  sleepHours: number;           // Number of hours slept
  dailySteps: number;           // Step count
  waterIntake: number;          // Water intake in ml or liters
  rewards: Rewards[];           // List of reward levels (enum values)
  scores: number[];             // List of performance scores
}
