* We currently had our concrete implementations of actions, conditions, and events in our authoring environment, but because these are serialized in authoring
to be deserialized in the runner, we needed to move them back into the engine to be serialized
* GravityComponent is now deprecated, as we decided we wanted to generalize movement to acceleration in either the x or y direction
* We added in Professor Duvall's reflection utility module so we have a uniform way of using reflection to avoid duplicated code
