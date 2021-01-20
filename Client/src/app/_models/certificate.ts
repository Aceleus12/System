export interface Certificate {
    id: string;
    name: string;
    description: string;
    firstStepId: number;
    certificateSteps: any[];
    cost: number;
}
