import { motion } from 'framer-motion'

function StatCard({ title, value, icon: Icon, change, color }) {
  const isPositive = change > 0
  const changeColor = isPositive ? 'text-success-500' : 'text-error-500'
  const changeIcon = isPositive ? '↑' : '↓'
  
  return (
    <motion.div
      whileHover={{ y: -4 }}
      className="card overflow-hidden"
    >
      <div className="flex items-start justify-between">
        <div>
          <p className="text-sm font-medium text-neutral-500">{title}</p>
          <p className="mt-1 text-2xl font-semibold text-neutral-900">{value}</p>
        </div>
        <div className={`p-3 rounded-lg ${color} bg-opacity-10`}>
          <Icon className={`h-6 w-6 ${color}`} />
        </div>
      </div>
      
      {change !== undefined && (
        <div className="mt-4 flex items-center">
          <span className={`text-xs font-medium ${changeColor} flex items-center`}>
            {changeIcon} {Math.abs(change)}%
          </span>
          <span className="text-xs text-neutral-500 ml-2">from last month</span>
        </div>
      )}
    </motion.div>
  )
}

export default StatCard