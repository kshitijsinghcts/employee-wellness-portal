import { genkit, z } from 'genkit';
import { googleAI } from '@genkit-ai/google-genai';

const ai = genkit({
  plugins: [googleAI()],
  model: googleAI.model('gemini-2.5-flash'),
});

// Define input/output schemas
const ChatInputSchema = z.object({
  message: z.string().describe('User message to the wellness assistant'),
});

const ChatOutputSchema = z.object({
  reply: z.string().describe('Bot reply to the user'),
});

// Define the flow
export const chatFlow = ai.defineFlow({
  name: 'chatFlow',
  inputSchema: ChatInputSchema,
  outputSchema: ChatOutputSchema,
}, async ({ message }) => {
  const prompt = `You are a wellness assistant. Respond to: ${message}`;
  const { text } = await ai.generate({ prompt });
  return { reply: text };
});