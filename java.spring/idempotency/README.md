
# How to achieve idempotency in POST method?

An idempotent HTTP method is a method that can be invoked many times without the different outcomes. It should not matter if the method has been called only once, or ten times over. The result should always be the same.
Idempotency essentially means that the result of a successfully performed request is independent of the number of times it is executed.

**This is a code example of how to make POST idempotent (again)**
