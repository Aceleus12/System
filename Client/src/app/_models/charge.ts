export interface Charge {
    currency: number;
    description: string;
    amount: number;
    stripeEmail: string;
    stripeToken: string;
}
