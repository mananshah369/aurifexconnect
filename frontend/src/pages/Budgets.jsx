import { motion } from 'framer-motion'
import { mockBudgets } from '../data/mockData'

function Budgets() {
  // Calculate percentage of budget spent
  const getPercentage = (spent, allocated) => {
    return Math.min(Math.round((spent / allocated) * 100), 100)
  }
  
  // Determine color based on percentage
  const getColorClass = (percentage) => {
    if (percentage < 50) return 'bg-success-500'
    if (percentage < 80) return 'bg-warning-500'
    return 'bg-error-500'
  }
  
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Budget Management
          </h1>
          <p className="mt-1 text-sm text-neutral-500">
            Track your spending against your budget allocations
          </p>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button className="btn btn-primary">
            Create New Budget
          </button>
        </div>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        <div className="card">
          <h3 className="text-lg font-semibold text-neutral-900 mb-2">Total Budget</h3>
          <p className="text-3xl font-bold text-primary-600">₹8,250.00</p>
          <p className="text-sm text-neutral-500 mt-1">Monthly allocation</p>
        </div>
        
        <div className="card">
          <h3 className="text-lg font-semibold text-neutral-900 mb-2">Total Spent</h3>
          <p className="text-3xl font-bold text-neutral-900">₹5,101.44</p>
          <p className="text-sm text-neutral-500 mt-1">62% of total budget</p>
        </div>
        
        <div className="card">
          <h3 className="text-lg font-semibold text-neutral-900 mb-2">Remaining</h3>
          <p className="text-3xl font-bold text-success-600">₹3,148.56</p>
          <p className="text-sm text-neutral-500 mt-1">38% of total budget</p>
        </div>
      </div>
      
      <div className="card">
        <h3 className="text-lg font-semibold text-neutral-900 mb-6">Budget Breakdown</h3>
        
        <div className="space-y-6">
          {mockBudgets.map((budget) => {
            const percentage = getPercentage(budget.spent, budget.allocated)
            const colorClass = getColorClass(percentage)
            
            return (
              <motion.div 
                key={budget.id}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3 }}
                className="space-y-2"
              >
                <div className="flex justify-between items-center">
                  <div>
                    <h4 className="text-sm font-semibold text-neutral-900">{budget.category}</h4>
                    <p className="text-xs text-neutral-500">{budget.period}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm font-medium text-neutral-900">
                      ₹{budget.spent.toFixed(2)} of ₹{budget.allocated.toFixed(2)}
                    </p>
                    <p className="text-xs text-neutral-500">{percentage}% spent</p>
                  </div>
                </div>
                
                <div className="w-full bg-neutral-200 rounded-full h-2.5">
                  <motion.div 
                    className={`h-2.5 rounded-full ${colorClass}`}
                    initial={{ width: 0 }}
                    animate={{ width: `${percentage}%` }}
                    transition={{ duration: 1, ease: "easeOut" }}
                  ></motion.div>
                </div>
              </motion.div>
            )
          })}
        </div>
        
        <div className="mt-8 border-t border-neutral-200 pt-6">
          <button className="btn btn-secondary">
            + Add Budget Category
          </button>
        </div>
      </div>
    </motion.div>
  )
}

export default Budgets