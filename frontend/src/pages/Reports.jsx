import { useState } from 'react'
import { motion } from 'framer-motion'
import { 
  ResponsiveContainer, BarChart, Bar, PieChart, Pie, Cell, 
  XAxis, YAxis, CartesianGrid, Tooltip, Legend, LineChart, Line 
} from 'recharts'
import { 
  mockMonthlyIncome, mockMonthlyExpenses, mockExpensesByCategory 
} from '../data/mockData'

function Reports() {
  const [reportType, setReportType] = useState('income-expense')
  
  // Chart color settings
  const COLORS = ['#10B981', '#3B82F6', '#F97316', '#8B5CF6', '#EC4899', '#EF4444', '#F59E0B']
  
  const formatYAxis = (value) => {
    if (value >= 1000) {
      return `₹${value / 1000}k`
    }
    return `₹${value}`
  }
  
  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-white p-3 shadow rounded border border-neutral-200">
          <p className="font-semibold">{label}</p>
          {payload.map((entry, index) => (
            <p key={index} className="text-sm" style={{ color: entry.color }}>
              {entry.name}: ₹{entry.value.toLocaleString()}
            </p>
          ))}
        </div>
      )
    }
    return null
  }
  
  const PieChartTooltip = ({ active, payload }) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-white p-3 shadow rounded border border-neutral-200">
          <p className="font-semibold">{payload[0].name}</p>
          <p className="text-sm font-medium" style={{ color: payload[0].color }}>
            ${payload[0].value.toLocaleString()} ({payload[0].percent.toFixed(1)}%)
          </p>
        </div>
      )
    }
    return null
  }
  
  // Merge income and expense data for comparison chart
  const compareData = mockMonthlyIncome.map((item, index) => ({
    month: item.month,
    Income: item.amount,
    Expenses: mockMonthlyExpenses[index].amount,
    Profit: item.amount - mockMonthlyExpenses[index].amount
  }))
  
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Financial Reports
          </h1>
          <p className="mt-1 text-sm text-neutral-500">
            Comprehensive analysis of your financial performance
          </p>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button className="btn btn-secondary">
            Export Report
          </button>
        </div>
      </div>
      
      {/* Report selector */}
      <div className="card mb-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <button 
            className={`py-3 px-4 text-center rounded-lg border ${
              reportType === 'income-expense'
                ? 'bg-primary-50 border-primary-300 text-primary-700'
                : 'bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50'
            }`}
            onClick={() => setReportType('income-expense')}
          >
            <h3 className="font-medium">Income vs Expenses</h3>
            <p className="text-sm text-neutral-500 mt-1">Monthly comparison</p>
          </button>
          
          <button 
            className={`py-3 px-4 text-center rounded-lg border ${
              reportType === 'category'
                ? 'bg-primary-50 border-primary-300 text-primary-700'
                : 'bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50'
            }`}
            onClick={() => setReportType('category')}
          >
            <h3 className="font-medium">Expense Breakdown</h3>
            <p className="text-sm text-neutral-500 mt-1">By category</p>
          </button>
          
          <button 
            className={`py-3 px-4 text-center rounded-lg border ${
              reportType === 'profit'
                ? 'bg-primary-50 border-primary-300 text-primary-700'
                : 'bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50'
            }`}
            onClick={() => setReportType('profit')}
          >
            <h3 className="font-medium">Profit Analysis</h3>
            <p className="text-sm text-neutral-500 mt-1">Monthly profit trends</p>
          </button>
        </div>
      </div>
      
      {/* Chart area */}
      <div className="card">
        <h3 className="text-lg font-semibold text-neutral-900 mb-6">
          {reportType === 'income-expense' && 'Income vs Expenses'}
          {reportType === 'category' && 'Expense Breakdown by Category'}
          {reportType === 'profit' && 'Monthly Profit Margin'}
        </h3>
        
        <div className="h-96">
          {reportType === 'income-expense' && (
            <ResponsiveContainer width="100%" height="100%">
              <BarChart
                data={compareData}
                margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
              >
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis dataKey="month" />
                <YAxis tickFormatter={formatYAxis} />
                <Tooltip content={<CustomTooltip />} />
                <Legend />
                <Bar dataKey="Income" fill="#10B981" />
                <Bar dataKey="Expenses" fill="#EF4444" />
              </BarChart>
            </ResponsiveContainer>
          )}
          
          {reportType === 'category' && (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8 h-full">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={mockExpensesByCategory}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                    nameKey="name"
                    label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  >
                    {mockExpensesByCategory.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip content={<PieChartTooltip />} />
                </PieChart>
              </ResponsiveContainer>
              
              <div>
                <h4 className="text-base font-medium mb-4">Expense Categories</h4>
                <div className="space-y-3">
                  {mockExpensesByCategory.map((entry, index) => (
                    <div key={index} className="flex items-center">
                      <div 
                        className="w-4 h-4 rounded mr-2" 
                        style={{ backgroundColor: COLORS[index % COLORS.length] }}
                      ></div>
                      <div className="flex-1">
                        <span className="text-sm font-medium">{entry.name}</span>
                      </div>
                      <div className="text-right">
                        <span className="text-sm font-medium">₹{entry.value.toFixed(2)}</span>
                        <span className="text-xs text-neutral-500 ml-1">
                          ({((entry.value / mockExpensesByCategory.reduce((a, b) => a + b.value, 0)) * 100).toFixed(1)}%)
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          )}
          
          {reportType === 'profit' && (
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                data={compareData}
                margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
              >
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis dataKey="month" />
                <YAxis tickFormatter={formatYAxis} />
                <Tooltip content={<CustomTooltip />} />
                <Legend />
                <Line 
                  type="monotone" 
                  dataKey="Profit" 
                  stroke="#4F46E5" 
                  strokeWidth={3}
                  dot={{ r: 4 }}
                  activeDot={{ r: 6 }}
                />
                <Line 
                  type="monotone" 
                  dataKey="Income" 
                  stroke="#10B981" 
                  strokeWidth={2}
                  strokeDasharray="5 5"
                />
                <Line 
                  type="monotone" 
                  dataKey="Expenses" 
                  stroke="#EF4444" 
                  strokeWidth={2}
                  strokeDasharray="5 5"
                />
              </LineChart>
            </ResponsiveContainer>
          )}
        </div>
      </div>
    </motion.div>
  )
}

export default Reports