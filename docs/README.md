# LeBron James Chatbot - The Playbook 

LeBron is a high-performance task management chatbot designed to help you stay on task with the consistency of a 4 time NBA champion. Whether you're tracking training sessions or daily chores, LeBron helps you manage your legacy with style. 

## Features

### 1. Adding Tasks
Add various types of tasks to your rotation.
* **Todo:** `todo [description]` — A simple task.
* **Deadline:** `deadline [description] /by [yyyy-MM-dd HHmm]` — A task with a due date.
* **Event:** `event [description] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]` — A task with a time range.

### 2. Viewing the Rotation
* **List:** `list` — LeBron displays all current tasks in your playbook.
* **Find:** `find [keyword]` — Search for specific tasks by keyword.

### 3. Managing Progress
* **Mark:** `mark [index]` — Marks a task as completed (Ring season!).
* **Unmark:** `unmark [index]` — Reverts a task to incomplete.
* **Update:** `update [index] [new description]` — Edits the description of an existing task.
* **Delete:** `delete [index]` — Removes a task from the rotation (Bench it!).

### 4. Exit
* **Bye:** `bye` — Closes the application. LeBron heads to the locker room.

## Usage Guide

### Command Format
* Words in `[brackets]` are parameters you provide.
* Dates must follow the `yyyy-MM-dd HHmm` format (e.g., `2024-12-25 1800`).

### Example Scenarios
1. **Adding a Deadline:**
   `deadline Game 7 /by 2026-06-20 2000`
2. **Updating a Play:**
   `update 1 Full Court Press`
3. **Finding a Keyword:**
   `find Game`