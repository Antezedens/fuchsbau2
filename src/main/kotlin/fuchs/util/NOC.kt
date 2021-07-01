package fuchs.util

object NOC: Comparator<String> {


	private fun Char.isDigit() = this in '0'..'9'

	/**
	 * @param a first string
	 * @param b second string
	 * @return zero iff `s` and `t` are equal,
	 * a value less than zero iff `s` lexicographically precedes `t`
	 * and a value larger than zero iff `s` lexicographically follows `t`
	 */
	override fun compare(a: String, b: String): Int {
		var aIndex = 0
		var bIndex = 0

		val aLength = a.length
		val bLength = b.length

		while (true) {
			// both character indices are after a subword (or at zero)

			// Check if one string is at end
			if (aIndex == aLength && bIndex == bLength) {
				return 0
			}
			if (aIndex == aLength) {
				return -1
			}
			if (bIndex == bLength) {
				return 1
			}

			// Compare sub word
			var aChar = a[aIndex]
			var bChar = b[bIndex]

			var aCharIsDigit = aChar.isDigit()
			var bCharIsDigit = bChar.isDigit()

			if (aCharIsDigit && bCharIsDigit) {
				// Compare numbers

				// skip leading 0s
				var aLeadingZeroCount = 0
				while (aChar == '0') {
					++aLeadingZeroCount
					++aIndex
					if (aIndex == aLength) {
						break
					}
					aChar = a[aIndex]
				}
				var bLeadingZeroCount = 0
				while (bChar == '0') {
					++bLeadingZeroCount
					++bIndex
					if (bIndex == bLength) {
						break
					}
					bChar = b[bIndex]
				}
				val aAllZero = aIndex == aLength || !aChar.isDigit()
				val bAllZero = bIndex == bLength || !bChar.isDigit()
				if (aAllZero) {
					return if (bAllZero) {
						continue
					} else {
						-1
					}
				}
				if (bAllZero) {
					return 1
				}

				var diff = 0
				do {
					if (diff == 0) {
						diff = aChar - bChar
					}
					++aIndex
					++bIndex
					if (aIndex == aLength && bIndex == bLength) {
						return if (diff != 0) diff else aLeadingZeroCount - bLeadingZeroCount
					}
					if (aIndex == aLength) {
						if (diff == 0) {
							return -1
						}
						return if (b[bIndex].isDigit()) -1 else diff
					}
					if (bIndex == bLength) {
						if (diff == 0) {
							return 1
						}
						return if (a[aIndex].isDigit()) 1 else diff
					}
					aChar = a[aIndex]
					bChar = b[bIndex]
					aCharIsDigit = aChar.isDigit()
					bCharIsDigit = bChar.isDigit()
					if (!aCharIsDigit && !bCharIsDigit) {
						// both number sub words have the same length
						if (diff != 0) {
							return diff
						}
						break
					}
					if (!aCharIsDigit) {
						return -1
					}
					if (!bCharIsDigit) {
						return 1
					}
				} while (true)
			} else {
				do {
					if (aChar != bChar) {
						val result = aChar.toUpperCase() - bChar.toUpperCase()
						return if (result != 0) result else aChar - bChar
					}
					++aIndex
					++bIndex
					if (aIndex == aLength && bIndex == bLength) {
						return 0
					}
					if (aIndex == aLength) {
						return -1
					}
					if (bIndex == bLength) {
						return 1
					}
					aChar = a[aIndex]
					bChar = b[bIndex]
					aCharIsDigit = aChar.isDigit()
					bCharIsDigit = bChar.isDigit()
				} while (!aCharIsDigit && !bCharIsDigit)
			}
		}
	}
}
