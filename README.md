# Meta-Merge

[![Build Status](https://travis-ci.org/weavejester/meta-merge.svg?branch=master)](https://travis-ci.org/weavejester/meta-merge)

This is a standalone implementation of the [Leiningen][] `meta-merge`
function. It's particularly useful for merging configuration maps.

[Leiningen]: https://github.com/technomancy/leiningen

## Installation

To install, add the following to your project `:dependencies`:

    [meta-merge "0.1.0-SNAPSHOT"]

## Usage

The `meta-merge` function recursively merges two data structures.

```clojure
(require '[meta-merge.core :refer [meta-merge]])

(meta-merge {:a [:b :c]} {:a [:d]})
=> {:a [:b :c :d]}
```

Metadata hints can be provided to override the default behavior:

```clojure
(meta-merge {:a [:b :c]} {:a ^:prepend [:d]})
=> {:a [:d :b :c]}

(meta-merge {:a [:b :c]} {:a ^:replace [:d]})
=> {:a [:d]}

(meta-merge {:a [:b :c]} {:a ^:displace [:d]})
=> {:a [:b :c]}
```

## License

Copyright © 2014 Phil Hagelberg, James Reeves and all the Leiningen
[contributors][].

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[contributors]: https://github.com/technomancy/leiningen/graphs/contributors
