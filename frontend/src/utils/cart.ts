// utils/cart.ts

export type CartProduct = {
    id: number;
    name: string;
    price: number;
};

const CART_KEY = "cart";

export function getCart(): CartProduct[] {
    const raw = localStorage.getItem(CART_KEY);
    return raw ? JSON.parse(raw) : [];
}

export function addToCart(product: CartProduct) {
    const cart = getCart();
    cart.push(product);
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
}

export function removeFromCart(index: number) {
    const cart = getCart();
    cart.splice(index, 1);
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
}

export function clearCart() {
    localStorage.removeItem(CART_KEY);
}
