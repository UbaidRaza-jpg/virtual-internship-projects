# Data Processor UML Diagram

This folder contains a UML class diagram for the processor component of the data processing pipeline.

- `diagram.mmd`: The source Mermaid diagram.
- `class-diagram.pdf`: The rendered PDF diagram.

The diagram models the `Processor` which uses the Strategy pattern to dynamically swap `ProcessingMode` (Dump, Passthrough, Validate) and `Database` (Postgres, Redis, Elastic) based on configuration.
