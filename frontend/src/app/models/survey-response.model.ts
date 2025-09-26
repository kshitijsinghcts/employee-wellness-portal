export interface SurveyResponse {
  responseId: number;                 // Auto-generated primary key
  surveyId: number;                   // ID of the associated survey
  employeeId: number;                 // ID of the employee who submitted the response
  answers: { [question: string]: string }; // Map of question-answer pairs
}
