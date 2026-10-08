# Pet Products ER Diagram

This directory contains the normalized 3NF Entity Relationship Diagram (ERD) for the Pet Products database.

## Files
- `erd.mmd`: The source Mermaid ER Diagram file.
- `erd.pdf`: The rendered PDF of the ER Diagram.
- `config.json` & `custom.css`: Configuration to enforce a pure black-and-white print style.

## Model Summary
The database tracks:
- **Products**: Including shared properties and specialized sub-entities for Pet Food, Pet Toy, and Pet Apparel.
- **Manufacturers**: Every product is manufactured by one manufacturer.
- **Animals**: Products can be associated with multiple animals (resolved via `ProductAnimal`).
- **Customers & Transactions**: Customers make transactions containing multiple products.
- **Shipments**: Shipments move products between locations.
