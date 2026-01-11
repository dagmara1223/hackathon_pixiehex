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
  - Spring Data JPA
  - Spring Security
  - H2
  - Scheduled jobs (cron-based)
  - REST API 

### Frontend
  - React
  - TypeScript

## Core features
### Orders
- Create orders without registration
- Store delivery address per order
- Pay only a deposit initially
- Get mail about the order status update
 - Add multiple products to a cart
 - Finalize orders with delivery details
 - Individual orders are grouped into group orders
  
  ### SingleOrder
A `SingleOrder` represents one product ordered by one user.

Each order contains:
- `productName`
- `userEmail`
- `shippingAddress`
- `productWeight`
- `originalPrice`
- `depositAmount`
- `finalPrice`
- `remainingToPay`
- `status`
- `orderDate`

- 
### Order Statuses
- `OPEN`
- `LOCKED`
- `PAID`
- `CANCELLED`

Orders are **identified by email**, not by user ownership.  
Users can create orders **without registering**.

## Security
 - Role-based authorization (USER, ADMIN)
 - Public endpoints for browsing and ordering
 - Admin-only endpoints for management actions
 - Spring Security with endpoint-level access control
 - CORS enabled for frontend integration

# Automated Processing (Scheduling)

The system uses Spring’s scheduler with a weekly cycle:

### Saturday – Lock Orders
- Locks eligible `OPEN` orders

### Monday – Processing & Cleanup
- Cancels unpaid `LOCKED` orders
- Runs batching logic:
  - Creates group orders
  - Splits shipping costs
  - Locks finalized orders
  - Generates PDFs
  No manual triggering is required in production.

##  Shipping Cost Distribution

- Shipping price depends on total group weight
- Cost is split proportionally by weight
- VAT (23%) is applied

Each order receives:
- Final price
- Remaining amount to pay

## How to run
Backend
```
mvn spring-boot:run
```

Frontend ```
npm install
```
npm run dev

