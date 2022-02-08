package net.gemelen.dev.pmt.math

object Solver {
  def solve(xs: List[Int], target: Int): List[List[Int]] =
    xs
      .combinations(2)
      .collect {
        case pair if pair.sum == target => pair
      }
      .toList
}
