# Standalone AVRO Schema

This project is a tool for combining AVRO schemas from multiple interdependent files into a single, standalone `avsc` file. 
It is designed to simplify the process of managing and deploying AVRO schemas, particularly when working with schema registry.

## Multiple Independent Files

- **Code Reusability:** With independent files, commonly used types (e.g., user, address, or product schemas) can be defined once and reused across different schemas, reducing duplication and ensuring consistency.
- **Simplified Collaboration:** Independent files allow teams to work on specific parts of the schema independently, improving development workflow and reducing conflicts. 
- **Maintainability:** Changes to shared schemas can be made in one place, automatically propagating to all schemas that reference them, simplifying updates and debugging.
- **Modularity:** Independent files encourage modular design, where schemas are logically grouped and managed.

## Standalone File in Schema Registry
- **Deployment Simplicity:** Most schema registries require a single, standalone .avsc file for deployment. Combining multiple files into one makes it ready for registry integration.
- **Error Reduction:** A standalone file ensures that all references are resolved, reducing the chances of runtime errors due to missing or unresolved schemas.
- **Standardization:** A unified schema file conforms to the requirements of most schema registries, ensuring compatibility and ease of use.

This plugin combines the best of both world. 
It enables developers to work with modular, 
reusable schema files during development and generates a standalone .avsc file for deployment.
