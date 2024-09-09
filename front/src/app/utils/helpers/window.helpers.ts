/**
 * Checks if the given CSS media query matches the current window.
 *
 * @param cssMediaQuery - The CSS media query to match.
 * @returns Returns `true` if the `cssMediaQuery` matches the current window, otherwise returns `false`.
 * @throws Throws an error if the window object is not available.
 * @example
 * const isMatching = matchesCssMediaQuery("width <= 600px");
 * console.log(isMatching); // true or false
 */
export function matchesCssMediaQuery(cssMediaQuery: string): boolean | null {
  const isNotAvailable: boolean = typeof window === 'undefined';
  if (isNotAvailable) {
    return false;
  }

  return window.matchMedia(`(${cssMediaQuery})`).matches;
}
