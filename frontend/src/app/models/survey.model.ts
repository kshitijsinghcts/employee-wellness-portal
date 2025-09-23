export interface Survey {
  surveyId: number;           // Auto-generated primary key
  surveyTitle: string;        // Title of the survey
  surveyQuestions: string[];  // List of questions in the survey
}
