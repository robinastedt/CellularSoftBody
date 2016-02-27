trait Physical {
  def position : Position
  def velocity : Velocity
  def acceleration : Acceleration
  def force : Force
}

trait eulerForward {
  this: Physical =>
  def step(dt : Float) {
    position += (velocity, dt)
    velocity += (acceleration, dt)
    acceleration += (force, dt)
  }
}
