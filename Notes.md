
## How I built it

I read through the Swift files to understand the screen structure,
data model, and business logic. Then I built the Android version
using Jetpack Compose for the UI, a ViewModel with StateFlow for
state management, and Gson to parse the sessions.json from assets.
The weight conversion and speed logic are in pure Kotlin functions
with unit tests covering the exact examples from the instructions.

## Creative feature

I added two features — a Best Set summary at the bottom of each
card showing the heaviest set of that session, and a tag filter
at the top of the screen to filter sessions by tag like Competition
or Paused. The best set gives athletes a quick performance summary
per session, and the tag filter helps them find specific session
types quickl

## What I would do with more time

With more time I would add a details screen for each session showing
a chart across sets so athletes can visually track their performance trends
over time.
