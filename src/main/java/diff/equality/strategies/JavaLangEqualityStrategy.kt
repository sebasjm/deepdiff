package diff.equality.strategies

import diff.equality.EqualityStrategy
import diff.equality.Pair

/**

 * @author Sebastian Marchano sebasjm@gmail.com
 */
class JavaLangEqualityStrategy : EqualityStrategy<Any> {

    override fun compare(before: Any, after: Any) =
        if (before.equals(after))
            EqualityStrategy.CompareResult.EQUALS
        else
            EqualityStrategy.CompareResult.DIFFERENT

    override fun shouldUseThisStrategy(cls: Class<Any>) =
        cls.`package`?.name?.startsWith("java") ?: false

}
