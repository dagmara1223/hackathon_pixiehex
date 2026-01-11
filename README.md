# hackathon_pixiehex

International shipping from Korea is often expensive.
K-Shipping solves this problem by enabling:

 - multiple users to order products

 - grouping those orders into one shipment

 - splitting shipping costs fairly

 - automating order processing and notifications

## Technology Stack
### Backend
  - Java 21
  - Spring Boot

### Frontend
  - React
  - TypeScript

## Core features
 - Browse available products
 - Create orders without registration
 - Add multiple products to a cart
 - Finalize orders with delivery details
 - Track order status via email
 - Individual orders are grouped into group orders
 - Group orders go through shipping stages:
    - OPEN
    - ON_THE_WAY
    - DELIVERED

## Security
 - Role-based authorization (USER, ADMIN)
 - Public endpoints for browsing and ordering
 - Admin-only endpoints for management actions
 - Spring Security with endpoint-level access control
 - CORS enabled for frontend integration

## How to run
Backend
mvn spring-boot:run

Frontend
npm install
npm run dev

