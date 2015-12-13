(ns meta-merge.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [meta-merge.core-test]))

(doo-tests 'meta-merge.core-test)
