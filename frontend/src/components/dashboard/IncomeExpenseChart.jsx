import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts'

function IncomeExpenseChart({ incomeData, expenseData }) {
  // Merge income and expense data
  const chartData = incomeData.map((item, index) => ({
    month: item.month,
    Income: item.amount,
    Expenses: expenseData[index].amount,
  }))
  
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
          <p className="text-sm text-success-600">
            Income: ₹{payload[0].value.toLocaleString()}
          </p>
          <p className="text-sm text-error-600">
            Expenses: ₹{payload[1].value.toLocaleString()}
          </p>
          <p className="text-sm font-medium mt-1">
            Net: ₹{(payload[0].value - payload[1].value).toLocaleString()}
          </p>
        </div>
      )
    }
    return null
  }
  
  return (
    <div className="card h-full">
      <h3 className="text-lg font-semibold text-neutral-900 mb-4">Income vs Expenses</h3>
      <div className="h-80">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart
            data={chartData}
            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
          >
            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
            <XAxis dataKey="month" axisLine={false} tickLine={false} />
            <YAxis tickFormatter={formatYAxis} axisLine={false} tickLine={false} />
            <Tooltip content={<CustomTooltip />} />
            <Legend wrapperStyle={{ paddingTop: 20 }} />
            <Bar dataKey="Income" fill="#10B981" radius={[4, 4, 0, 0]} />
            <Bar dataKey="Expenses" fill="#EF4444" radius={[4, 4, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  )
}

export default IncomeExpenseChart