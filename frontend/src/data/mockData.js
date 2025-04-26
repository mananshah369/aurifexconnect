// Mock transaction data
export const mockTransactions = [
  {
    id: 't1',
    date: '2025-01-01T08:30:00Z',
    description: 'Client Payment - ABC Corp',
    amount: 2500,
    type: 'income',
    category: 'sales',
    account: 'Business Checking',
    status: 'completed'
  },
  {
    id: 't2',
    date: '2025-01-03T12:15:00Z',
    description: 'Office Supplies',
    amount: 125.45,
    type: 'expense',
    category: 'office',
    account: 'Business Credit Card',
    status: 'completed'
  },
  {
    id: 't3',
    date: '2025-01-05T14:20:00Z',
    description: 'Software Subscription',
    amount: 49.99,
    type: 'expense',
    category: 'software',
    account: 'Business Credit Card',
    status: 'completed'
  },
  {
    id: 't4',
    date: '2025-01-10T09:45:00Z',
    description: 'Client Payment - XYZ Ltd',
    amount: 1800,
    type: 'income',
    category: 'sales',
    account: 'Business Checking',
    status: 'completed'
  },
  {
    id: 't5',
    date: '2025-01-15T13:30:00Z',
    description: 'Office Rent',
    amount: 1200,
    type: 'expense',
    category: 'rent',
    account: 'Business Checking',
    status: 'completed'
  },
  {
    id: 't6',
    date: '2025-01-18T11:20:00Z',
    description: 'Utility Bills',
    amount: 175.25,
    type: 'expense',
    category: 'utilities',
    account: 'Business Checking',
    status: 'completed'
  },
  {
    id: 't7',
    date: '2025-01-22T15:10:00Z',
    description: 'Consulting Services',
    amount: 950,
    type: 'income',
    category: 'consulting',
    account: 'Business Checking',
    status: 'completed'
  },
  {
    id: 't8',
    date: '2025-01-25T10:30:00Z',
    description: 'Marketing Expenses',
    amount: 350.75,
    type: 'expense',
    category: 'marketing',
    account: 'Business Credit Card',
    status: 'completed'
  },
  {
    id: 't9',
    date: '2025-01-28T09:15:00Z',
    description: 'Client Retainer',
    amount: 1500,
    type: 'income',
    category: 'retainer',
    account: 'Business Checking',
    status: 'pending'
  },
  {
    id: 't10',
    date: '2025-01-30T16:45:00Z',
    description: 'Employee Salary',
    amount: 3200,
    type: 'expense',
    category: 'salary',
    account: 'Business Checking',
    status: 'scheduled',
    scheduledDate: '2023-11-01T00:00:00Z'
  }
]

// Mock account data
export const mockAccounts = [
  {
    id: 'a1',
    name: 'Business Checking',
    type: 'checking',
    balance: 8524.30,
    currency: 'INR',
    institution: 'BANK 1'
  },
  {
    id: 'a2',
    name: 'Business Savings',
    type: 'savings',
    balance: 15250.00,
    currency: 'INR',
    institution: 'BANK 2'
  },
  {
    id: 'a3',
    name: 'Business Credit Card',
    type: 'credit',
    balance: -2340.75,
    currency: 'INR',
    institution: 'BANK 3',
    limit: 10000
  },
  {
    id: 'a4',
    name: 'Investment Account',
    type: 'investment',
    balance: 32500.00,
    currency: 'INR',
    institution: 'BANK 4'
  }
]

// Mock category data
export const mockCategories = [
  { id: 'c1', name: 'Rent', type: 'expense', color: '#FF6B6B' },
  { id: 'c2', name: 'Utilities', type: 'expense', color: '#4ECDC4' },
  { id: 'c3', name: 'Salary', type: 'expense', color: '#1A535C' },
  { id: 'c4', name: 'Office', type: 'expense', color: '#FFE66D' },
  { id: 'c5', name: 'Software', type: 'expense', color: '#6B48FF' },
  { id: 'c6', name: 'Marketing', type: 'expense', color: '#FF9F1C' },
  { id: 'c7', name: 'Sales', type: 'income', color: '#2EC4B6' },
  { id: 'c8', name: 'Consulting', type: 'income', color: '#011627' },
  { id: 'c9', name: 'Retainer', type: 'income', color: '#41EAD4' }
]

// Mock budget data
export const mockBudgets = [
  { id: 'b1', category: 'Rent', allocated: 1200, spent: 1200, period: 'monthly' },
  { id: 'b2', category: 'Utilities', allocated: 250, spent: 175.25, period: 'monthly' },
  { id: 'b3', category: 'Office', allocated: 500, spent: 125.45, period: 'monthly' },
  { id: 'b4', category: 'Software', allocated: 300, spent: 49.99, period: 'monthly' },
  { id: 'b5', category: 'Marketing', allocated: 1000, spent: 350.75, period: 'monthly' },
  { id: 'b6', category: 'Salary', allocated: 5000, spent: 3200, period: 'monthly' }
]

// Mock report data
export const mockMonthlyExpenses = [
  { month: 'Jan', amount: 5800 },
  { month: 'Feb', amount: 5900 },
  { month: 'Mar', amount: 6100 },
  { month: 'Apr', amount: 5950 },
  { month: 'May', amount: 5850 },
  { month: 'Jun', amount: 6200 },
  { month: 'Jul', amount: 6300 },
  { month: 'Aug', amount: 6150 },
  { month: 'Sep', amount: 6050 },
  { month: 'Oct', amount: 5900 },
  { month: 'Nov', amount: 0 },
  { month: 'Dec', amount: 0 }
]

export const mockMonthlyIncome = [
  { month: 'Jan', amount: 7500 },
  { month: 'Feb', amount: 6800 },
  { month: 'Mar', amount: 7200 },
  { month: 'Apr', amount: 7000 },
  { month: 'May', amount: 7400 },
  { month: 'Jun', amount: 7800 },
  { month: 'Jul', amount: 8200 },
  { month: 'Aug', amount: 7900 },
  { month: 'Sep', amount: 8100 },
  { month: 'Oct', amount: 7750 },
  { month: 'Nov', amount: 0 },
  { month: 'Dec', amount: 0 }
]

export const mockExpensesByCategory = [
  { name: 'Rent', value: 1200 },
  { name: 'Utilities', value: 175.25 },
  { name: 'Office', value: 125.45 },
  { name: 'Software', value: 49.99 },
  { name: 'Marketing', value: 350.75 },
  { name: 'Salary', value: 3200 }
]

// Mock invoice data
export const mockInvoices = [
  {
    id: 'inv-001',
    client: 'ABC Corp',
    issueDate: '2025-01-01',
    dueDate: '2025-01-15',
    amount: 2500,
    status: 'paid',
    items: [
      { description: 'Web Development', quantity: 1, rate: 2500, amount: 2500 }
    ]
  },
  {
    id: 'inv-002',
    client: 'XYZ Ltd',
    issueDate: '2025-01-05',
    dueDate: '2025-01-20',
    amount: 1800,
    status: 'paid',
    items: [
      { description: 'Consulting Services', quantity: 6, rate: 300, amount: 1800 }
    ]
  },
  {
    id: 'inv-003',
    client: 'Acme Inc',
    issueDate: '2025-01-12',
    dueDate: '2025-01-27',
    amount: 3200,
    status: 'unpaid',
    items: [
      { description: 'UX/UI Design', quantity: 1, rate: 1200, amount: 1200 },
      { description: 'Frontend Development', quantity: 1, rate: 2000, amount: 2000 }
    ]
  },
  {
    id: 'inv-004',
    client: 'Global Solutions',
    issueDate: '2025-01-20',
    dueDate: '2023-11-04',
    amount: 4500,
    status: 'unpaid',
    items: [
      { description: 'Brand Strategy', quantity: 1, rate: 1500, amount: 1500 },
      { description: 'Marketing Campaign', quantity: 1, rate: 3000, amount: 3000 }
    ]
  },
  {
    id: 'inv-005',
    client: 'Tech Innovators',
    issueDate: '2025-01-25',
    dueDate: '2023-11-09',
    amount: 1750,
    status: 'draft',
    items: [
      { description: 'Software Maintenance', quantity: 1, rate: 1750, amount: 1750 }
    ]
  }
]