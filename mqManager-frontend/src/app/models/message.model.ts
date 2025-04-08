export interface Message {
  id: number;
  messageId: string;
  content: string;
  receivedAt: string;
  sourceApplication: string;
  queueName: string;
}