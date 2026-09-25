# Ensaf8 — MapActivity Refactoring Handoff

> **Document purpose:** This file is a shared technical context and handoff document for developers, AI assistants, and future work sessions involving the Ensaf8 Android project.
>
> **Current status:** `MapActivity` and the latest `MapBottomSheetManager` update have been tested by the project owner and reported to be working correctly.

---

## 1. Project Identity

### Ensaf vs Ensaf8

These terms must not be treated as interchangeable:

- **Ensaf** = the overall product, concept, domain model, product vision, roadmap, product decisions, and long-term architecture.
- **Ensaf8** = the current Android implementation of Ensaf.

Future implementations such as Web, Backend, API, Admin, or later Android versions remain part of the broader Ensaf product.

An implementation convenience in Ensaf8 must not silently redefine the Ensaf product model.

---

## 2. Current Project Scope

The current engineering focus is the incremental refactoring of:

```text
MapActivity.java
```

The purpose of this work is to make the code easier to understand, maintain, debug, and extend while preserving the existing application behavior.

The refactoring is structural. It is **not** a permission to redesign the product, domain model, database, repositories, queries, or user workflows.

---

## 3. Main Refactoring Rule

> **CHANGE STRUCTURE. PRESERVE BEHAVIOR.**

Unless the project owner explicitly requests a behavioral change, the following must remain unchanged:

- User-facing behavior
- Existing business logic
- Database schema and database behavior
- Repository behavior
- Query behavior
- Existing workflows
- Existing event flows
- Existing domain concepts
- Existing synchronization assumptions
- Existing behavior of the map and BottomSheet features

Do not silently introduce improvements, redesigns, optimizations, or unrelated cleanup during a structural extraction.

---

## 4. MapActivity Role

`MapActivity` remains the **Coordinator**.

It may coordinate the extracted components, lifecycle events, callbacks, UI interactions, map operations, and data flows. It should not lose necessary coordination responsibilities merely for the purpose of making the file smaller.

The extracted classes are organizational boundaries. They are not an automatic requirement to move every method or every line of code into a separate class.

Extraction boundaries must be based on verified:

- Responsibilities
- Dependencies
- Shared state
- Android lifecycle requirements
- Callback relationships
- Database and query interactions
- UI and event flows
- Google Maps/osmdroid-related interactions, where applicable

---

## 5. Initial Target Structure

The initial target structure contains the following classes:

```text
MapActivity
├── MapController.java
├── MapRenderer.java
├── MapEventHandler.java
├── MapLocationManager.java
├── MapPermissionManager.java
├── MapMarkerManager.java
├── MapFilterManager.java
├── MapBottomSheetManager.java
├── MapSearchManager.java
├── MapQueryManager.java
├── MapSyncManager.java
└── MapUtils.java
```

This list is a working target and starting point for analysis. It must not be treated as a reason to force artificial abstractions or to change behavior.

A different boundary may be acceptable if source-code evidence shows that it better preserves responsibilities, dependencies, lifecycle safety, and existing behavior.

---

## 6. Current Baseline

### Latest relevant files

- `MapActivity.java`
- `MapBottomSheetManager.java`

The project owner has stated that the latest GitHub versions of both files have been tested and work correctly.

GitHub links:

- [MapActivity.java](https://github.com/Ensaf8/Ensaf8/blob/main/app/src/main/java/com/parandak/ensaf8/mapPage/MapActivity.java)
- [MapBottomSheetManager.java](https://github.com/Ensaf8/Ensaf8/blob/main/app/src/main/java/com/parandak/ensaf8/mapPage/MapBottomSheetManager.java)

### Baseline rule

The latest working GitHub version is the baseline for future work.

Do not use any of the following as the primary source of truth when the current GitHub source is available:

- An older local copy
- A remembered code version
- An AI-generated reconstruction
- An earlier conversation snippet
- A proposed implementation that has not been tested

Before making a change, inspect the current source version and compare the proposed change against the working baseline.

---

## 7. MapBottomSheetManager Status

`MapBottomSheetManager` is an extracted component of the MapActivity refactoring process.

The latest version has been tested by the project owner and reported to work correctly.

Therefore:

- Treat the current implementation as a working baseline.
- Do not reimplement its behavior from memory.
- Do not redesign its responsibilities without source-based analysis.
- Do not move code back into `MapActivity` merely because the extracted class is complex.
- Do not change its interactions with existing data, UI, repositories, queries, or workflows without explicit approval.

Any future modification must first identify the exact behavior being preserved and the reason the change is necessary.

---

## 8. MapUtils Boundary

The current approved scope of `MapUtils` is limited to:

```text
hideKeyboard()
distance()
deg2rad()
rad2deg()
```

`getGeoPointList()` must not be moved into `MapUtils` based on the current refactoring decision.

The relevant polygon-related flow is:

```text
getGeoPointList()
        ↓
getPolygonList()
        ↓
initPolygonList()
        ↓
showOnMapPolygon()
```

Because `getGeoPointList()` participates directly in the existing polygon construction and presentation flow, it should remain with the responsibility that owns that flow unless a future, source-based decision explicitly changes this boundary.

The polygon feature is not permission to redesign the existing data model or introduce new polygon behavior during unrelated refactoring.

---

## 9. Database, Repository, and Query Boundaries

MapActivity refactoring must not silently modify the data layer.

Known project context includes:

- Database: `Ensaf8_CRM`
- Database version: `1`
- Database helper: `DBHelper_CRM`
- Database access: `DatabaseManager`
- Repository Pattern
- Query classes used by feature areas

Known tables include:

```text
individual
gpoint
indi_geop
indi_coop
cons_phase
bookmark
bookmark_type
tend
atten
cusaccount
phonenum
place_geop
rating
```

The following must be preserved during MapActivity refactoring unless explicitly requested otherwise:

- Existing SQL queries
- Query conditions and joins
- Repository responsibilities
- Database relationships
- Data ordering
- Existing insert, update, and delete behavior
- Existing historical or audit-related behavior
- Existing domain meaning of Individuals, locations, tasks, and related records

If the source code is unclear, inspect the relevant Query, Repository, Model, and database classes before making a claim or proposing a change.

---

## 10. Evidence and Decision Rules

Every technical statement or proposal must be classified mentally as one of the following:

### A. Verified fact

Directly confirmed by the current source code, project file, test result, or explicit project-owner statement.

### B. Established decision

A decision already recorded in the project decision log, such as `DEC-001` or `DEC-002`.

### C. Interpretation

A reasoned understanding of the existing code or architecture. Interpretations must be clearly identified and must not be presented as confirmed facts.

### D. Proposal

A new possible approach that has not yet been approved. A proposal must not be presented as an existing project decision.

When evidence is insufficient, state what is missing and ask a focused question. Do not guess.

---

## 11. No-Guessing Rule

Before making technical claims or changes:

1. Read the current source code.
2. Identify the relevant dependencies.
3. Trace the existing behavior and event flow.
4. Check lifecycle-sensitive code.
5. Check database, repository, and query interactions.
6. Identify shared state and callbacks.
7. Make the smallest necessary change.
8. Verify that behavior is preserved.

Never invent:

- Classes
- Methods
- Fields
- Database relationships
- Query behavior
- Repository behavior
- Lifecycle behavior
- APIs
- Dependencies
- Existing test results

If a required source file is unavailable, explicitly state that it is unavailable.

---

## 12. Recommended Refactoring Workflow

For each extraction or modification:

### Step 1 — Inspect

Read the current GitHub version of the relevant files.

### Step 2 — Map responsibilities

Identify the methods, state, dependencies, callbacks, lifecycle requirements, UI elements, and data flows involved.

### Step 3 — Define the smallest boundary

Choose an extraction boundary that preserves behavior and avoids unnecessary coupling.

### Step 4 — Extract incrementally

Move only the code supported by source evidence. Avoid simultaneous changes across unrelated architectural layers.

### Step 5 — Integrate

Reconnect references, callbacks, lifecycle handling, and shared state without changing the existing workflow.

### Step 6 — Test

Test the affected user flows and check for regressions. Do not claim that a change works until it has actually been tested.

### Step 7 — Record

Update this document or the decision log only after the resulting behavior and architectural decision are verified.

---

## 13. Active Decisions

### DEC-001 — Ensaf vs Ensaf8

**Status:** ACTIVE

- Ensaf = product and conceptual model.
- Ensaf8 = current Android implementation.

### DEC-002 — MapActivity Refactoring

**Status:** ACTIVE

- `MapActivity` remains the Coordinator.
- Refactoring is incremental and evidence-based.
- Existing behavior must be preserved.
- No Business Logic change.
- No Database change.
- No Repository change.
- No Query change.
- No silent change to the Ensaf conceptual model.

The decision log is authoritative for established project decisions. A new proposal that conflicts with an active decision must explicitly identify the conflict and request confirmation before replacing it.

---

## 14. How a New Developer or AI Should Start

When continuing this project:

1. Read this README completely.
2. Understand that Ensaf and Ensaf8 are different scopes.
3. Read the current GitHub versions of `MapActivity.java` and the relevant extracted classes.
4. Treat the current source code as the implementation source of truth.
5. Read the active decision log, especially `DEC-001` and `DEC-002`.
6. Identify the exact task or requested change.
7. Separate verified facts from interpretations and proposals.
8. Trace the existing behavior before suggesting a refactor.
9. Make the smallest change necessary.
10. Preserve behavior unless a behavioral change has been explicitly requested.
11. Test the affected workflow.
12. Document only verified results and approved decisions.

Do not begin by rewriting the architecture or by assuming that all twelve target classes must be implemented immediately.

---

## 15. Product and Architecture Context

The broader Ensaf product includes concepts such as:

- User
- Individual
- Roles and relationships of Individuals
- Task / Tend
- Creator
- Assignee / Actor
- Action
- Target
- Related Individuals

These concepts belong to the product/domain level and must not be changed merely to simplify an Android implementation.

Known broader areas of ongoing or incomplete work include:

- Tend / Task
- Reminder
- Search
- Polygon
- Construction Phase filtering
- Synchronization
- Offline-first behavior

Do not use the MapActivity refactoring as an opportunity to redesign these areas unless a separate, explicit task is created.

---

## 16. Master Rule

```text
Do not guess.

Inspect the current source first.

Understand existing behavior before changing structure.

Change the smallest necessary surface.

Preserve Business Logic, Database, Repository, Query, and user-facing behavior.

Do not silently replace an established decision.

Test before claiming success.
```

---

## Document Metadata

| Item | Current value |
|---|---|
| Product | Ensaf |
| Android implementation | Ensaf8 |
| Current engineering focus | MapActivity refactoring |
| Coordinator | MapActivity |
| Latest extracted component | MapBottomSheetManager |
| Current reported status | Tested and working |
| Primary source of implementation truth | GitHub repository |
| Active decision | DEC-002 |
| Refactoring principle | Change structure, preserve behavior |

---

**Last updated:** 2026-09-26
