export interface WellnessMetric {
  metricId: number;
  employeeId: number;
  recordDate: string; // Using string to match "2025-09-23" format
  mood: string;
  sleepHours: number;
  dailySteps: number;
  waterIntake: number;
  rewards: string[];
  scores: number[];
}
