export interface Resource {
  resourceId: number;         // Auto-generated primary key
  title: string;              // Title of the resource
  type: string;               // "video" or "article"
  content: string;            // Large text content (CLOB)
  resourceTags: string[];     // List of tags assigned to the resource
  resourceCategory: string;   // Category of the resource
}
